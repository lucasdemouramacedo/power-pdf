"use client";

import Layout from "@/layouts/Layout";
import List from "@/components/List";
import Link from "@/components/Link";
import MergeForm from "@/components/MergeForm";

export default function Home() {
    return (
        <Layout>
            <MergeForm />
            <List />
            <div className="float-right"><Link link="/report" enableLabel="Report" disableLabel="Pendente" /></div>

        </Layout>
    );
}
