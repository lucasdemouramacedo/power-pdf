'use client';

import { createContext, useContext, useState, ReactNode, useCallback } from 'react';
import { MergedFile, MergedFilesContextState } from '@/types';
import { getFiles } from '@/services/MergeService';

const MergedFilesContext = createContext<MergedFilesContextState | undefined>(undefined);

interface MergedFilesProviderProps {
  children: ReactNode;
}

export function MergedFilesProvider({ children }: MergedFilesProviderProps) {
  const [files, setFiles] = useState<MergedFile[] | null>(null);
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

      const data: MergedFile[] = await getFiles(finalStartDate, finalEndDate);
      
      setFiles(data);
      setCurrentDates({ startDate: finalStartDate, endDate: finalEndDate });
    } catch (err: any) {
      setError(err.message || 'Ocorreu um erro desconhecido.');
      setFiles([]);
    } finally {
      setLoading(false);
    }
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