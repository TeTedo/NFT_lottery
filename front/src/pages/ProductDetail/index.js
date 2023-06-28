import React from "react";
import { Container, Contents, Left, Right } from "./styles";
import LeftUpComponents from "./Left/LeftUpComponents";
import LeftDownComponents from "./Left/LeftDownComponents";
import RightUpComponents from "./Right/RightUpComponents";
import RightDownComponents from "./Right/RightDownComponents";

function ProductDetail() {
  const items = {
    Total: 10,
    Remain: 2,
    FlowPrice: 0.001,
    CurrentPrice: 0.001,
  };
  return (
    <Container>
      <Contents.Container>
        <Left.Contents>
          <LeftUpComponents />
          <LeftDownComponents />
        </Left.Contents>
        <Right.Contents>
          <RightUpComponents />
          <RightDownComponents items={items} />
        </Right.Contents>
      </Contents.Container>
    </Container>
  );
}

export default ProductDetail;
