import React from "react";
// import { HoldingContents } from "./styles";
import { TicketContents } from "./styles";
import ShortenAddress from "components/TopBar/ShortenAddress";

export function TicketLists() {
  return (
    <TicketContents.Container>
      <TicketContents.Category>
        <TicketContents.CategoryItem>Raffle ID</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>NFT Name</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>Token ID</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>
          Ticket Quantity
        </TicketContents.CategoryItem>
      </TicketContents.Category>
      <TicketContents.Category>
        <TicketContents.CategoryItem>
          레플 아이디
        </TicketContents.CategoryItem>
        <TicketContents.CategoryItem>NFT 이름</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>토큰아이디</TicketContents.CategoryItem>
        <TicketContents.CategoryItem>1</TicketContents.CategoryItem>
      </TicketContents.Category>
    </TicketContents.Container>
  );
}

export default TicketLists;
