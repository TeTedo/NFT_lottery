import React from "react";
// import { HoldingContents } from "./styles";
import { TicketContents } from "./styles";

export function TicketLists() {
  return (
    <TicketContents.Container>
      
      <TicketContents.Category>
        <TicketContents.CategoryItem>Raffle ID</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>NFT Name</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>Token ID</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>Token ID</TicketContents.CategoryItem>
      </TicketContents.Category>
    </TicketContents.Container>
  );
}

export default TicketLists;
