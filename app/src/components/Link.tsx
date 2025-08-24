type Props = {
    link: string;
    enableLabel: string;
    disableLabel: string;
}

export default function Link( props: Props) {

    return (
        <div>
            { props.link && (
                <a href={ props.link} className="inline-block text-center text-white bg-blue-500 py-[2px] w-[130px] rounded-lg font-normal text-sm no-underline">
                    {props.enableLabel}
                </a>
            )}

            { ! props.link && (
                <div className=" text-center bg-white text-blue-500 py-[0px] w-[130px] rounded-lg font-normal text-sm border-2 border-dotted border-blue-500">
                    {props.disableLabel}
                </div>
            )}
        </div>
    );
}
