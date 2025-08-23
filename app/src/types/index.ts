export type DateRange = {
    start: string | null;
    end: string | null;
};

export interface MergedFile {
  id: string;
  name: string;
  link: string;
  createdAt: string;
}

export interface MergedFilesContextState {
  files: MergedFile[] | null;
  loading: boolean;
  error: string | null;
  fetchFiles: (startDate: string | null, endDate: string | null) => Promise<void>;
}

export interface NotificationContextState {
  showInfo: (message: string) => void;
  showError: (message: string) => void;
}