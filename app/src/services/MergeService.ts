export async function getFiles(start: any, end: any) {
  const res = await fetch(`http://localhost:8080/merge?startDate=${start}&endDate=${end}`, { cache: "no-store" });
  if (!res.ok) throw new Error("Erro ao buscar arquivos");
  return res.json();
}

export async function newMerge(merge: any) {
  const res = await fetch(`http://localhost:8080/merge`, {
    method: "POST",
    body: merge,
  });

  return res;
}
