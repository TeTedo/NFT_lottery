import React from "react";
import { Container, Contents, Title, Content } from "./styles";

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
        <Title>Contract Address</Title>
        <Content> {items.address}</Content>
      </Contents>
      <Contents>
        <Title>TokenID</Title>
        <Content> {items.tokenID}</Content>
      </Contents>
      <Contents>
        <Title>TokenStandard</Title>
        <Content> {items.tokenStandard}</Content>
      </Contents>
      <Contents>
        <Title>Blockchain</Title>
        <Content> {items.blockchain}</Content>
      </Contents>
      <Contents>
        <Title>Creator Fees</Title>
        <Content> {items.creatorFees}</Content>
      </Contents>
    </Container>
  );
}

export default ContentsDetail;
