export async function getReport(start: any, end: any) {
  const res = await fetch(`http://localhost:8080/report?startDate=${start}&endDate=${end}`, { cache: "no-store" });
  if (!res.ok) throw new Error("Erro buscar relatório");
  return res.json();
}