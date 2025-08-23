"use client";

import Layout from "@/layouts/Layout";
import Input from "@/components/Input";
import Button from "@/components/Button";
import { useState, FormEvent } from 'react';
import PdfUploadThumbnail from "@/components/PdfUploadThumbnail";
import List from "@/components/List";
import InputFile from "@/components/InputFile";
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
                <InputFile files={formData.files} label="Nome do pdf" id="fileName" name="fileName" onChange={handleFileChange} />
                <Input type="text" label="Nome do pdf" id="fileName" name="fileName" onChange={handleInputChange} value={formData.fileName} />
                <Button label="Realizar o merge" />
            </form>
            <List />
        </Layout>
    );
}
