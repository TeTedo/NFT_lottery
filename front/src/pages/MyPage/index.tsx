import React, { useState } from "react";
import { Container, Contents, Left, Right } from "./styles";
import Holding from "components/MyPage/Holding";
import Sell from "components/MyPage/Sell";
import Winner from "components/MyPage/Winner";

function MyPage() {
  const [value, setValue] = useState<string>("holding");

  function SellHandler() {
    setValue("sell");
  }
  function HoldingHandler() {
    setValue("holding");
  }
  function WinnerHandler() {
    setValue("winner");
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
            <Left.Content name="winner" value={value} onClick={WinnerHandler}>
              당첨
            </Left.Content>
          </Left.Container>
          <Right.Container>
            {value === "holding" ? (
              <Holding />
            ) : value === "sell" ? (
              <Sell />
            ) : (
              <Winner />
            )}
          </Right.Container>
        </Contents.Container>
      </Container>
    </>
  );
}

export default MyPage;
