import React, { useState } from "react";
import { HoldingContents } from "./styles";
import ProductList from "components/Common/ProductList";
import TicketLists from "./TicketLists";

export function Holding() {
  const [NFTValue, setNFTValue] = useState<string>("NFT");
  function NFTHandler() {
    setNFTValue("NFT");
  }
  function TicketHandler() {
    setNFTValue("Ticket");
  }
  return (
    <>
      <HoldingContents.Menu>
        <HoldingContents.Item name="NFT" value={NFTValue} onClick={NFTHandler}>
          NFT
        </HoldingContents.Item>
        <HoldingContents.Item
          name="Ticket"
          value={NFTValue}
          onClick={TicketHandler}
        >
          TICKET
        </HoldingContents.Item>
      </HoldingContents.Menu>
      {NFTValue === "NFT" ? (
        <ProductList category="holding" />
      ) : (
        <TicketLists />
      )}
    </>
  );
}

export default Holding;
