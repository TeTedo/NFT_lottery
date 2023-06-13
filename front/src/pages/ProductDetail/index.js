import React from "react";
import {
  Container,
  LeftUpContent,
  LeftDownContent,
  RightUpContent,
  RightDownContent,
  Contents,
  LeftContents,
  RigthContents,
} from "./styles";

function ProductDetail() {
  return (
    <Container>
      <Contents>
        <LeftContents>
          <LeftUpContent>
            <div>Details</div> 
            ProductDetail1
            </LeftUpContent>
          <LeftDownContent>ProductDetail2</LeftDownContent>
        </LeftContents>
        <RigthContents>
          <RightUpContent>ProductDetail3</RightUpContent>
          <RightDownContent>ProductDetail4</RightDownContent>
        </RigthContents>
      </Contents>
    </Container>
  );
}

export default ProductDetail;
