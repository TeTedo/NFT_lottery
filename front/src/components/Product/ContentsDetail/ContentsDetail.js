import React from "react";
import { Container, Contents, Tilte, Content } from "./styles";

function ContentsDetail() {
  const items = {
    address: "0x2358...a68b",
    tokenID: "7883",
    tokenStandard: "ERC-721",
    blockchain: "Ethereum",
    creatorFees: "5%",
  };
  return (
    <Container>
      <Contents>
        <Tilte>Contract Address</Tilte>
        <Content> {items.address}</Content>
      </Contents>
      <Contents>
        <Tilte>TokenID</Tilte>
        <Content> {items.tokenID}</Content>
      </Contents>
      <Contents>
        <Tilte>TokenStandard</Tilte>
        <Content> {items.tokenStandard}</Content>
      </Contents>
      <Contents>
        <Tilte>Blockchain</Tilte>
        <Content> {items.blockchain}</Content>
      </Contents>
      <Contents>
        <Tilte>Creator Fees</Tilte>
        <Content> {items.creatorFees}</Content>
      </Contents>
    </Container>
  );
}

export default ContentsDetail;
