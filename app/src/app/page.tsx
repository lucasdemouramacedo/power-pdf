"use client";

import Layout from "@/layouts/Layout";
import Input from "@/components/Input";
import Button from "@/components/Button";
import { useState, FormEvent } from 'react';
import PdfUploadThumbnail from "@/components/PdfUploadThumbnail";
import Filter from "@/components/Filter";
import { DateRange } from "@/types";
import List from "@/components/List";

type Form = {
    fileName: string;
    files: FileList | null;
};

export default function Home() {
    const [formData, setFormData] = useState<Form>({
        fileName: '',
        files: null,
    });



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
            <form action="">
                <div className="bg-white w-[100%] h-[200px] rounded-lg p-7 border-1 border-gray-300">
                    <div className="w-[100%] h-[100%] rounded-lg border-3 border-dashed border-blue-300">
                        <PdfUploadThumbnail filesURLs={Array.from(formData.files || []).map(file => URL.createObjectURL(file))}></PdfUploadThumbnail>
                    </div>
                </div>
                <Input type="text" label="Nome do pdf" id="fileName" name="fileName" onChange={handleInputChange} value={formData.fileName} />
                {
                    //<Input type="file" accept="application/pdf" label="Selecione os PDF's" id="files" name="files" onChange={handleFileChange} value={""} multiple />
                }
                <Button label="Realizar o merge" />
            </form>
            <List />
        </Layout>
    );
}
