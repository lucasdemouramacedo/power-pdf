"use client";

import Layout from "@/layouts/Layout";
import Input from "@/components/Input";
import Button from "@/components/Button";
import { useState, FormEvent } from 'react';
import PdfUploadThumbnail from "@/components/PdfUploadThumbnail";
import Filter from "@/components/Filter";
import { DateRange } from "@/types/DateRange";

type Form = {
    fileName: string;
    files: FileList | null;
};

export default function Home() {
    const [formData, setFormData] = useState<Form>({
        fileName: '',
        files: null,
    });

    const [currentDates, setCurrentDates] = useState<DateRange | null>(null);
    const handleFilterUpdate = (dates: DateRange) => {
        console.log('Datas recebidas do componente filho:', dates);
        setCurrentDates(dates);
    };

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
        console.log(formData)
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        if (e.target.files) {
            setFormData((prev) => ({ ...prev, files: e.target.files }));
        }
        console.log(formData)
    };

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        try {
            const response = await fetch('/api/upload', {
                method: 'POST',
                body: JSON.stringify(formData),
                headers: {
                    'Content-Type': 'application/json',
                },
            });

            const result = await response.json();

            if (response.ok) {
                setFormData({ fileName: '', files: null });
                (e.target as HTMLFormElement).reset();
            }
        } catch (error) {
            console.error('Erro ao enviar o formulário:', error);
        }
    };

    return (
        <Layout>
            <div className="bg-white w-[100%] h-[200px] rounded-lg p-7 border-1 border-gray-300">
                <div className="w-[100%] h-[100%] rounded-lg border-3 border-dashed border-blue-300">
                    <PdfUploadThumbnail filesURLs={Array.from(formData.files || []).map(file => URL.createObjectURL(file))}></PdfUploadThumbnail>
                </div>
            </div>
            <Input type="text" label="Nome do pdf" id="fileName" name="fileName" onChange={handleInputChange} value={formData.fileName} />
            <Input type="file" accept="application/pdf" label="Selecione os PDF's" id="files" name="files" onChange={handleFileChange} value={""} multiple />
            <Button label="Realizar o merge" />
            <div className="flex items-center justify-between flex-col sm:flex-row">
                <div>
                    <span className="text-sm font-bold text-blue-500">Merges</span>
                    <span className="bg-gray-300 rounded-lg px-2 w-fit h-fit text-blue-gray-500 ml-2 font-bold text-[0.75rem]">0</span>
                </div>
                <Filter onDateChange={handleFilterUpdate} />
            </div>

        </Layout>
    );
}
