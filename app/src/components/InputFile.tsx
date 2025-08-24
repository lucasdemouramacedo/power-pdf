
import { useState } from "react";
import PdfUploadThumbnail from "./PdfUploadThumbnail";

type InputProps = {
    label: string;
    id: string;
    name: string;
    error?: string | null;
    multiple?: boolean;
    accept?: string;
    files: File[];
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function InputFile(props: InputProps) {
    const [fileURLs, setFileURLs] = useState<string[]>([]);

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;
        const urls = files ? Array.from(files).map(file => URL.createObjectURL(file)) : [];
        setFileURLs(urls);
        
        props.onChange(e);
    };

    return (
        <div>
            <label htmlFor={props.id}>
                <div className="bg-white w-[100%] h-[200px] rounded-lg p-7 border-1 border-gray-300">
                    <div className={`w-[100%] h-[100%] rounded-lg border-3 border-dashed ${!props.error ? 'border-blue-300' : 'border-red-300' }`}>
                        {props.files.length > 0 && <PdfUploadThumbnail filesURLs={fileURLs}></PdfUploadThumbnail>}
                        {props.files.length === 0 && <div className="h-[100%] flex items-center pl-5 font-regular text-gray-300"> {props.label} </div>}
                    </div>
                </div>
            </label>
            <input
                id={props.id}
                name={props.name}
                type="file"
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