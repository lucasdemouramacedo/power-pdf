"use client";

import { useState, useEffect } from "react";
import Spinner from "./Spinner";

export default function PdfUploadThumbnail({ filesURLs }: { filesURLs: Array<string> }) {
  const [thumbnails, setThumbnails] = useState<string[]>([]);
  const [pdfjsLib, setPdfjsLib] = useState<any>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [isProcessing, setIsProcessing] = useState(false);

  useEffect(() => {
    import("./../../public/pdf.mjs")
      .then((module) => {
        module.GlobalWorkerOptions.workerSrc = `${window.location.origin}/pdf.worker.mjs`;
        setPdfjsLib(module);
        setIsLoading(false);
      })
      .catch((err) => {
        console.error("Failed to load pdf.js:", err);
        setIsLoading(false);
      });
  }, []);

  useEffect(() => {
    const renderThumbnails = async () => {
      if (!pdfjsLib || filesURLs.length === 0) {
        return;
      }

      setIsProcessing(true);

      const thumbnailPromises = filesURLs.map(async (url) => {
        try {
          const loadingTask = pdfjsLib.getDocument(url);
          const pdf = await loadingTask.promise;
          const page = await pdf.getPage(1);
          const viewport = page.getViewport({ scale: 0.5 });
          const canvas = document.createElement("canvas");
          const context = canvas.getContext("2d");
          canvas.height = viewport.height;
          canvas.width = viewport.width;

          await page.render({ canvasContext: context, viewport }).promise;
          return canvas.toDataURL("image/png");
        } catch (err) {
          console.error("Failed to render PDF:", url, err);
          return null;
        }
      });

      const generatedThumbnails = await Promise.all(thumbnailPromises);
      const validThumbnails = generatedThumbnails.filter((t): t is string => t !== null);
      setThumbnails(validThumbnails);
      setIsProcessing(false);
    };

    renderThumbnails();
  }, [filesURLs, pdfjsLib]);

  return (
    <div className="bg-white rounded-md overflow-hidden h-full">
      {isProcessing && thumbnails.length === 0 && <Spinner />}
      {!isProcessing && thumbnails.length > 0 && 
      <div className="flex gap-4 p-2 bg-white overflow-x-auto h-full">
          {!isProcessing && thumbnails.map((thumbnail, index) => (
            <div key={index} className="flex-shrink-0 h-full border border-gray-200 rounded-sm overflow-hidden">
              <img
                src={thumbnail}
                className="h-full"
              />
            </div>
          ))}
        </div>
      }
    </div>
  );
}