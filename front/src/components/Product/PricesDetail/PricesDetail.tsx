import React from "react";
import { Container, Contents, Price, Quantity, From } from "./styles";

export function PricesDetail() {
  const items = {
    Price: "1.476WETH",
    Quantity: "1",
    From: "7B247A",
  };
  return (
    <Container>
      <Contents>
        <Price>{items.Price}</Price>
        <Quantity> {items.Quantity}</Quantity>
        <From> {items.From}</From>
      </Contents>
    </Container>
  );
}

export default PricesDetail;
