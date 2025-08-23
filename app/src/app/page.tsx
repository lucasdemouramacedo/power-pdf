"use client";

import Layout from "@/layouts/Layout";
import List from "@/components/List";
import MergeForm from "@/components/MergeForm";

export default function Home() {
    return (
        <Layout>
            <MergeForm />
            <List />
        </Layout>
    );
}
