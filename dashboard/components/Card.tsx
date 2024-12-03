import { benchEndpointToUrl } from "@/lib/utils";
import Link from "next/link";

interface CardProps {
  benchName: string;
  url: string;
}

export const Card = ({ url, benchName }: CardProps) => {
  const dashBoardUrl = benchEndpointToUrl(url);

  return (
    <Link href={dashBoardUrl || ""} className="max-w-96 px-5 py-3 shadow-lg rounded-md border">
      Acessar bancada {benchName}
    </Link>
  );
};
