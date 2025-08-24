type InputProps = {
    label: string;
    type: string;
    id: string;
    name: string;
    value?: string;
    error?: string | null;
    multiple?: boolean;
    accept?: string;
    onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
}

export default function Input(props:InputProps) {
    return (
        <div className="w-full my-4">
            <input
                id={props.id}
                name={props.name}
                type={props.type || "text"}
                className={`w-[100%] h-[50px] bg-white rounded-lg border-1 
                focus:border-blue-500 pl-5 outline-0 font-regular placeholder-gray-300  text-{14px} ${!props.error ? 'border-gray-300' : 'border-red-300' }`}
                placeholder={props.label}
                {...(props.type !== "file" ? { value: props.value } : {})}
                onChange={props.onChange}
                multiple={props.multiple}
                accept={props.accept}
            />
            <p className="mt-1 text-xs text-red-600">
                {props.error && (
                    props.error
                )}
            </p>
        </div>
    );
}