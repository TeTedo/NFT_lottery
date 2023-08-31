import React from "react";
import { SellContents } from "./styles";

import ProductList from "components/Common/ProductList";

export function Sell() {
  return (
    <>
      <SellContents.Menu>
        <SellContents.Item>NFT</SellContents.Item>
        <SellContents.Button>모두 받기</SellContents.Button>
      </SellContents.Menu>
      <ProductList category="sell" />
    </>
  );
}

export default Sell;
