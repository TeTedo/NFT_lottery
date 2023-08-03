import React from "react";
import { LeftDown } from "../styles";
import PricesDetail from "../../../components/Product/PricesDetail/PricesDetail";

function LeftDownComponents() {
  return (
    <LeftDown.Content>
      <LeftDown.PriceMenu>
        <LeftDown.PriceTitle>Price</LeftDown.PriceTitle>
        <LeftDown.PriceTitle>Quantity</LeftDown.PriceTitle>
        <LeftDown.PriceTitle>From</LeftDown.PriceTitle>
      </LeftDown.PriceMenu>
      <PricesDetail />
      <PricesDetail />
      <PricesDetail />
      <PricesDetail />
      <PricesDetail />
    </LeftDown.Content>
  );
}

export default LeftDownComponents;
