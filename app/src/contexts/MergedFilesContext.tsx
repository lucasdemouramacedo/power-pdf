'use client';

import { createContext, useContext, useState, ReactNode, useCallback } from 'react';
import { MergedFile, MergedFilesContextState } from '@/types';

const MergedFilesContext = createContext<MergedFilesContextState | undefined>(undefined);

interface MergedFilesProviderProps {
  children: ReactNode;
}

export function MergedFilesProvider({ children }: MergedFilesProviderProps) {
  const [files, setFiles] = useState<MergedFile[]>([]);
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const [currentDates, setCurrentDates] = useState<{ startDate: string, endDate: string } | null>(null);

  const fetchFiles = useCallback(async (startDate: string | null, endDate: string | null) => {
    setLoading(true);
    setError(null);
    
    try {
      let finalStartDate = startDate;
      let finalEndDate = endDate;

      if (!finalStartDate || !finalEndDate) {
        const today = new Date();
        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0');
        const day = String(today.getDate()).padStart(2, '0');
        const todayString = `${year}-${month}-${day}`;

        finalStartDate = finalStartDate || todayString;
        finalEndDate = finalEndDate || todayString;
      }

      const url = `http://localhost:8080/merge?startDate=${finalStartDate}&endDate=${finalEndDate}`;
      const response = await fetch(url);

      if (!response.ok) {
        throw new Error('Falha ao buscar dados. Status: ' + response.status);
      }

      const data: MergedFile[] = await response.json();
      setFiles(data);
      setCurrentDates({ startDate: finalStartDate, endDate: finalEndDate }); 
    } catch (err: any) {
      setError(err.message || 'Ocorreu um erro desconhecido.');
      setFiles([]);
    } finally {
      setLoading(false);
    }
    console.log('Arquivos buscados:', files);
  }, []);

  const value: MergedFilesContextState = {
    files,
    loading,
    error,
    fetchFiles
  };

  return (
    <MergedFilesContext.Provider value={value}>
      {children}
    </MergedFilesContext.Provider>
  );
}

export function useMergedFiles() {
  const context = useContext(MergedFilesContext);

  if (context === undefined) {
    throw new Error('useMergedFiles deve ser usado dentro de um MergedFilesProvider');
  }

  return context;
}