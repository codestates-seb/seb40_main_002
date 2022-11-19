type Props = {
  date: (string | number)[];
  ghname: string | null;
  payMoney: number | null;
  url: string | null;
  reply: string[] | null;
  ratedScore: number | null;
  filterDate: (string | number)[];
  totalMoney: number | null;
};

type PropsMix = {
  ghData: Props;
  paymentRole: string;
  setPaymentRole: React.Dispatch<React.SetStateAction<string>>;
};

type PaymentRoleProp = {
  paymentRole: string;
  setPaymentRole: React.Dispatch<React.SetStateAction<string>>;
};
export { Props, PropsMix, PaymentRoleProp };
