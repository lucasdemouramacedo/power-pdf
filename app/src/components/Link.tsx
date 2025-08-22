type Props = {
    link: string;
}

export default function Link({ link }: Props) {

    return (
        <div>
            { link && (
                <a href={link} className="inline-block text-center text-white bg-blue-500 py-[2px] w-[130px] rounded-lg font-normal text-sm no-underline">
                    Download
                </a>
            )}

            { !link && (
                <div className=" text-center bg-white text-blue-500 py-[0px] w-[130px] rounded-lg font-normal text-sm border-2 border-dotted border-blue-500">
                    Pendente
                </div>
            )}
        </div>
    );
}
