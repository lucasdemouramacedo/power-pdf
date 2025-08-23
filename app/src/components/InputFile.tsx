
import { useState } from "react";
import PdfUploadThumbnail from "./PdfUploadThumbnail";

type InputProps = {
    label: string;
    id: string;
    name: string;
    error?: string | null;
    multiple?: boolean;
    accept?: string;
    files: FileList | null;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function InputFile(props: InputProps) {
    const [fileURLs, setFileURLs] = useState<string[]>([]);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;
        if (files) {
            const urls = Array.from(files).map(file => URL.createObjectURL(file));
            setFileURLs(urls);
        }
        props.onChange(e);
    };

    return (
        <div>
            <label htmlFor={props.id}>
                <div className="bg-white w-[100%] h-[200px] rounded-lg p-7 border-1 border-gray-300">
                    <div className={`w-[100%] h-[100%] rounded-lg border-3 border-dashed ${!props.error ? 'border-blue-300' : 'border-red-300' }`}>
                        <PdfUploadThumbnail filesURLs={fileURLs}></PdfUploadThumbnail>
                    </div>
                </div>
            </label>
            <input
                id={props.id}
                name={props.name}
                type="file"
                className={`w-[100%] h-[50px] bg-white rounded-lg border-1 
                border-gray-300 focus:border-blue-500 pl-5 outline-0 
                font-regular  text-{14px}`}
                onChange={handleFileChange}
                multiple={true}
                accept="application/pdf"
                hidden
            />
            <p className="mt-1 text-xs text-red-600">
                {props.error && (
                    props.error
                )}
            </p>
        </div>
    );
}