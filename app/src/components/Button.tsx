type ButtonProps = {
    label: string;
}

export default function Button(props:ButtonProps) {
    return (
        <button className="w-[100%] h-[50px] bg-blue-500 rounded-lg mb-4 border-gray-300 text-white font-bold text-{14px}">
            {props.label}
        </button>
    );
}