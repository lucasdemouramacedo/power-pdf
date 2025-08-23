import { MergedFilesProvider } from "@/contexts/MergedFilesContext";
import { NotificationProvider } from "@/contexts/NotificationContext";
import Image from "next/image";
import { ReactNode } from "react";

type LayoutProps = {
    children: ReactNode;
}
export default function Layout({ children }: LayoutProps) {
    return (
        <MergedFilesProvider>
            <NotificationProvider>
                <div className="font-sans grid grid-rows-[200px_1fr_120px] items-center justify-items-center min-h-screen">
                    <header className="flex items-center justify-center bg-blue-500 h-[100%] w-[100%]">
                        <Image
                            aria-hidden
                            src="/logo.png"
                            alt="Power PDF"
                            width={200}
                            height={200}
                            className="mb-[50px]"
                        />
                    </header>
                    <main className="h-[100%] w-[100%] flex items-start justify-center">
                        <div className="h-[100%] w-[100%] max-w-[800px] px-10 mt-[-50px]">{children}</div>
                    </main>
                    <footer className="flex items-center justify-center">
                        <Image
                            aria-hidden
                            src="/image.png"
                            alt=""
                            width={130}
                            height={130}
                        />
                    </footer>
                </div>
            </NotificationProvider>
        </MergedFilesProvider>
    );
}