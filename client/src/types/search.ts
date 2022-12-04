export interface DateRange {
  startDate: Date;
  endDate: Date;
  setDateRange: React.Dispatch<React.SetStateAction<(Date | null)[]>>;
}

export interface SearchOption {
  cityId: number;
  start: string;
  end: string;
  tags: string[];
}
