import React, { useState } from "react";
import { Container, Contents, Left, Right } from "./styles";
import ProductList from "components/Main/ProductList";

function MyPage() {
  const [value, setValue] = useState(true);
  const [NFTValue, setNFTValue] = useState(true);
  function SellHandler() {
    setValue(false);
  }
  function HoldingHandler() {
    setValue(true);
  }
  function NFTHandler() {
    setNFTValue(true);
  }
  function TicketHandler() {
    setNFTValue(false);
  }

  return (
    <>
      <Container>
        <Contents.Container>
          <Left.Container>
            <Left.Title>My Page</Left.Title>
            <Left.Content name="holding" value={value} onClick={HoldingHandler}>
              보유
            </Left.Content>
            <Left.Content name="sell" value={value} onClick={SellHandler}>
              판매
            </Left.Content>
          </Left.Container>
          <Right.Container>
            <Right.Menu>
              <Right.Item name="NFT" value={NFTValue} onClick={NFTHandler}>
                NFT
              </Right.Item>
              <Right.Item
                name="TICKET"
                value={NFTValue}
                onClick={TicketHandler}
              >
                TICKET
              </Right.Item>
            </Right.Menu>
            <ProductList />
          </Right.Container>
        </Contents.Container>
      </Container>
    </>
  );
}

export default MyPage;
