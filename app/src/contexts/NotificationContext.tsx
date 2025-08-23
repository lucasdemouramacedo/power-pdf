'use client';

import { NotificationContextState } from '@/types';
import { createContext, useState, ReactNode, useCallback, useContext, useEffect } from 'react';
import { FaInfoCircle } from "react-icons/fa";
import { MdError } from "react-icons/md";

const NotificationContext = createContext<NotificationContextState | undefined>(undefined);

export function NotificationProvider({ children }: { children: ReactNode }) {
  const [message, setMessage] = useState('');
  const [type, setType] = useState<'info' | 'error' | null>(null);

  useEffect(() => {
    if (type) {
      const timer = setTimeout(() => {
        setType(null);
        setMessage('');
      }, 4000);
      return () => clearTimeout(timer);
    }
  }, [type]);

  const showInfo = useCallback((msg: string) => {
    setMessage(msg);
    setType('info');
  }, []);

  const showError = useCallback((msg: string) => {
    setMessage(msg);
    setType('error');
  }, []);

  const value: NotificationContextState = {
    showInfo,
    showError,
  };

  return (
    <NotificationContext.Provider value={value}>
      {type && (
        <div
          className={`
            fixed bottom-10 right-12 z-50 p-4 rounded-lg shadow-xl flex items-center gap-3
            transition-transform transform animate-fade-in-down
            ${type === 'info' ? 'bg-blue-500 text-white' : ''}
            ${type === 'error' ? 'bg-red-500 text-white' : ''}
          `}
        >
          {type === 'info' && <FaInfoCircle size={20} />}
          {type === 'error' && <MdError size={20} />}
          <span>{message}</span>
        </div>
      )}
      {children}
    </NotificationContext.Provider>
  );
}

export function useNotification() {
  const context = useContext(NotificationContext);

  if (context === undefined) {
    throw new Error('useNotification deve ser usado dentro de um NotificationProvider');
  }

  return context;
}