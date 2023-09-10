import React from "react";
import { SellContents } from "./styles";

import ProductList from "components/Common/ProductList";

export function Sell() {
  return (
    <>
      <SellContents.Menu>
        <SellContents.Item>NFT</SellContents.Item>
        <div style={{ display: "flex", gap: "20px", alignItems: "center" }}>
          <div>받을 클레이 : 10KLAY</div>
          <SellContents.Button>모두 받기</SellContents.Button>
        </div>
      </SellContents.Menu>
      <ProductList category="sell" />
    </>
  );
}

export default Sell;
