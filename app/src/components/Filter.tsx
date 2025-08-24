import { useState } from 'react';
import { DateRange } from "@/types";

const formatDate = (date: Date) => {
  return date.toISOString().split("T")[0];
};

export default function Filter({ preset, onChangeDate }: { preset: string | null, onChangeDate: (dates: any) => void }) {
    const [rangeDate, setRangeDate] = useState<DateRange>({
        start: formatDate(new Date()),
        end: formatDate(new Date()),
    });

    const [activePreset, setActivePreset] = useState<string | null>(preset);

    const handleDateChange = (e: React.ChangeEvent<HTMLInputElement>, field: 'start' | 'end') => {
        const value = e.target.value;
        const newDate = value ? formatDate(new Date(value)) : null;

        setRangeDate(prev => ({ ...prev, [field]: newDate }));
        setActivePreset(null);

        onChangeDate({ ...rangeDate, [field]: newDate });
    };

    const handleSetPreset = (preset: string) => {
        const endDate = new Date();
        let startDate = new Date();

        if (preset === '7dias') {
            startDate.setDate(endDate.getDate() - 6);
        } else if (preset === '30dias') {
            startDate.setDate(endDate.getDate() -29);
        }

        setRangeDate({ start: formatDate(startDate), end: formatDate(endDate) });
        setActivePreset(preset);

        onChangeDate({ start: formatDate(startDate), end: formatDate(endDate) });
    };

    const presetClasses = "cursor-pointer text-blue-500 outline-none w-auto px-1";
    const activePresetClasses = "font-bold";

    return (
        <div className="bg-white rounded-md px-2 py-1 w-fit text-sm font-medium flex items-center">
            <span className="text-blue-gray-500">Filtro:</span>
            <input
                type="date"
                value={rangeDate.start ?? ""}
                onChange={(e) => handleDateChange(e, 'start')}
                className="text-blue-500 ml-2 outline-none w-[85px] [&::-webkit-calendar-picker-indicator]:hidden [&::-webkit-inner-spin-button]:hidden"
            />
            <span className="text-blue-gray-500 mx-2">-</span>
            <input
                type="date"
                value={rangeDate.end ?? ""}
                onChange={(e) => handleDateChange(e, 'end')}
                className="text-blue-500 ml-2 outline-none w-[85px] [&::-webkit-calendar-picker-indicator]:hidden [&::-webkit-inner-spin-button]:hidden"
            />
            <span className="text-blue-gray-500 mx-2 font-bold">|</span>
            <span
                onClick={() => handleSetPreset('hoje')}
                className={`${presetClasses} ${activePreset === 'hoje' ? activePresetClasses : ''}`}
            >
                Hoje
            </span>
            <span className="text-blue-gray-500 mx-2">-</span>
            <span
                onClick={() => handleSetPreset('7dias')}
                className={`${presetClasses} ${activePreset === '7dias' ? activePresetClasses : ''}`}
            >
                7 dias
            </span>
            <span className="text-blue-gray-500 mx-2">-</span>
            <span
                onClick={() => handleSetPreset('30dias')}
                className={`${presetClasses} ${activePreset === '30dias' ? activePresetClasses : ''}`}
            >
                30 dias
            </span>
        </div>
    );
}