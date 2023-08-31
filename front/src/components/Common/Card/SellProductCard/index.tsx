import React from "react";
import { Card } from "./styles";
import { useNavigation } from "hooks/useNavigation";

export default function SellProductCard() {
  const { goToDetail } = useNavigation();

  return (
    <>
      <Card.Container onClick={() => goToDetail("1")}>
        <Card.Img />
        <Card.Contents>
          <Card.Title>sell product description</Card.Title>
          <Card.Description>price</Card.Description>
          <Card.Time>left time</Card.Time>
        </Card.Contents>
      </Card.Container>
    </>
  );
}
