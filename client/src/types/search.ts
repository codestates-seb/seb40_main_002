export interface DateRange {
  startDate: Date;
  endDate: Date;
  setDateRange: React.Dispatch<React.SetStateAction<(Date | null)[]>>;
}

export interface SearchOption {
  cityId: string;
  start: string;
  end: string;
  tags: string[];
}
