type InputProps = {
    label: string;
    id: string;
    error?: string | null
}

export default function Input(props:InputProps) {
    return (
        <div className="w-full mb-4">
            <input
                id={props.id}
                className={`w-[100%] h-[50px] bg-white rounded-lg border-1 border-gray-300 focus:border-blue-500 pl-5 outline-0`}
                placeholder={props.label}
            />
            {props.error && (
                <p className="mt-1 text-xs text-red-600">
                    {props.error}
                </p>
            )}
        </div>
    );
}