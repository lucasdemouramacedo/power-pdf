"use client";

import Layout from "@/layouts/Layout";
import Input from "@/components/Input"
export default function Home() {
    return (
        <Layout>
            <div className="bg-white w-[100%] h-[200px] rounded-lg p-7 border-1 border-gray-300">
                <div className="w-[100%] h-[100%] rounded-lg border-3 border-dashed border-blue-300">
                    teste
                </div>
            </div>
            <Input label="Nome do pdf" id="fileName"></Input>
            
        </Layout>
    );
}
