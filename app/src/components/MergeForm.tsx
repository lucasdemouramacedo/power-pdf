import { FormEvent, useState } from "react";
import InputFile from "./InputFile";
import Input from "./Input";
import Button from "./Button";
import { newMerge } from "@/services/MergeService";
import { useNotification } from "@/contexts/NotificationContext"

type Form = {
    fileName: string;
    files: File[];
};

type Errors = {
    fileName: string;
    files: string;
};


export default function MergeForm() {
    const [formData, setFormData] = useState<Form>({
        fileName: '',
        files: [],
    });
    const [formErrors, setFormErrors] = useState<Errors>({
        fileName: '',
        files: '',
    });
    const { showInfo, showError } = useNotification();

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));
        setFormErrors((prev) => ({ ...prev, [name]: '' }));
    };

    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const files = e.target.files;
        setFormData((prev) => ({ ...prev, files: files ? Array.from(files) : [] }));
        setFormErrors((prev) => ({ ...prev, files: '' }));
    };

    const handleSubmit = async (e: FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const form = new FormData();
        form.append("fileName", formData.fileName);
        formData.files.forEach(file => form.append("files", file));

        const response = await newMerge(form);

        const responseJson = await response.json();
        if (response.status === 201) {
            showInfo("Merge solicitado com sucesso!");
            setFormData({ fileName: '', files: [] });
            setFormErrors({ fileName: '', files: '' });
        } else if (responseJson.errors) {
            setFormErrors((errors) => ({ ...errors, ...responseJson.errors }));
        } else {
            showError("Erro ao solicitar merge.");
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <InputFile files={formData.files} label="Selecione os PDF's" id="fileName" name="fileName" onChange={handleFileChange} error={formErrors.files} />
            <Input type="text" label="Nome do pdf" id="fileName" name="fileName" onChange={handleInputChange} value={formData.fileName} error={formErrors.fileName} />
            <Button label="Realizar o merge" />
        </form>
    );
}