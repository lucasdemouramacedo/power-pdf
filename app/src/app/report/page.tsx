"use client";
import dynamic from 'next/dynamic';
import 'chart.js/auto';
import Layout from "@/layouts/Layout";
import Filter from '@/components/Filter';
import { getReport } from '@/services/ReportService';
import { useEffect, useState } from 'react';
import Spinner from "@/components/Spinner";

const Line = dynamic(() => import('react-chartjs-2').then((mod) => mod.Bar), {
    ssr: false,
});

const formatDate = (date: Date) => {
    return date.toISOString().split("T")[0];
};

type ReportItem = {
    date: string;
    mergeRequestCount: number;
};

export default function Report() {
    const [report, setReport] = useState<ReportItem[]>([]);
    const [loading, setLoading] = useState(false);

    const chartData = {
        labels: report.map((item) => item.date),
        datasets: [
            {
                label: "Merges por dia",
                data: report.map((item) => item.mergeRequestCount),
                backgroundColor: "#177EF3",
                borderWidth: 0,
                borderRadius: Number.MAX_VALUE,
                borderSkipped: false,
            },
        ],
    };

    const handleDateChange = async (dates: { start: string; end: string }) => {
        setLoading(true);
        const dataReport = await getReport(dates.start, dates.end);
        setReport(dataReport);
        setLoading(false);
    };

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            const dataReport = await getReport(new Date(Date.now() - 6 * 24 * 60 * 60 * 1000).toISOString().split("T")[0], new Date().toISOString().split("T")[0]);
            setReport(dataReport);
            setLoading(false);
        };
        fetchData();
    }, []);

  return (
    <Layout>
      <div className="h-[100%] w-[100%] flex flex-col justify-center items-center">
        <div className="w-[100%] h-[380px] mt-10 bg-white p-5 rounded-xl">
                    {loading && <Spinner />}
                    {!loading && <Line data={chartData} />}
                </div>
                <div className="mt-10">
                    <Filter onChangeDate={handleDateChange} preset={"7dias"} />
                </div>
            </div>
        </Layout>
    );
}