import React from "react";

interface ShortenAddressProps {
  address: string;
}

const ShortenAddress: React.FC<ShortenAddressProps> = ({ address }) => {
  if (!address || address.length < 8) return null;

  const shortened = `${address.slice(0, 6)}...${address.slice(-6)}`;
  return <span>{shortened}</span>;
};

export default ShortenAddress;
