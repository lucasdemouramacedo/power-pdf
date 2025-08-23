import { useEffect, useState } from "react";
import Filter from "./Filter";
import { useMergedFiles } from "@/contexts/MergedFilesContext";
import { LuCalendarArrowUp } from "react-icons/lu";
import { CgFileDocument } from "react-icons/cg";
import Link from "./Link";
import Spinner from "./Spinner";
import Image from "next/image";

export default function List() {
    const { files, loading, error, fetchFiles } = useMergedFiles();

    useEffect(() => {
        fetchFiles(null, null);
    }, [fetchFiles]);
    return (
        <div className="">
            <div className="mt-15 mb-5 flex items-center justify-between flex-col sm:flex-row">
                <div>
                    <span className="text-sm font-bold text-blue-500">Merges</span>
                    <span className="bg-gray-300 rounded-lg px-2 w-fit h-fit text-blue-gray-500 ml-2 font-bold text-xs">
                        {(files ? files.length : 0)}
                    </span>
                </div>
                <Filter />
            </div>
            <div className="h-[298px] overflow-y-auto rounded-lg">
                {loading && <Spinner />}
                {!loading && !error && (files ? files.length === 0 : false) && (
                    <div className="h-[100%] w-[100%] border-t text-center flex flex-col justify-center items-center">
                        <Image
                            aria-hidden
                            src="/archive-icon.svg"
                            alt=""
                            width={100}
                            height={100}
                            className="mb-5"
                        />
                        <p className="text-md text-blue-gray-500 font-bold m-0 p-0">Nenhum merge encontrado</p>
                        <p className="text-sm text-blue-gray-500 font-regular m-0 p-0">Junte seus PDF's em um único documento</p>
                    </div>
                )}
                {!loading && !error && files && files.length > 0 && (
                    <ul>
                        {files.map((file) => (
                            <li key={file.id} className="text-blue-gray-500 h-[56px] mb-2 p-1 border-1 border-gray-300 bg-gray-200 grid rounded-lg grid-cols-[1fr_1fr_1fr] items-center px-9">
                                <div className="font-normal flex items-center">
                                    <LuCalendarArrowUp className="mr-2" />
                                    <span className="text-sm">{new Date(file.createdAt).toLocaleDateString()} {new Date(file.createdAt).getHours()}:{new Date(file.createdAt).getUTCMinutes()}</span>
                                </div>
                                <div className="font-normal flex items-center">
                                    <CgFileDocument className="text-lg mr-2" />
                                    <span className="text-sm">{file.name}</span>
                                </div>
                                <div className="flex items-center justify-center">
                                    <Link link={file.link} />
                                </div>
                            </li>
                        ))}
                    </ul>
                )}
            </div>
        </div>
    );
}
