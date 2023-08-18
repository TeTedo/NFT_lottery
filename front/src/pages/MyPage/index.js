import React from "react";
import { Container, Contents , Left,Right} from "./styles";
import ProductList from "components/Main/ProductList";

function MyPage() {
  return (
    <>
      <Container>
        <Contents.Container>
                <Left.Container>
                  <Left.Title>My Page</Left.Title>
                  <Left.Content>보유</Left.Content>
                  <Left.Content>판매</Left.Content>
                </Left.Container>
                <Right.Container>
                    <Right.Menu>
                      <Right.Item>NFT</Right.Item>
                      <Right.Item>TICKET</Right.Item>
                    </Right.Menu>
                    <ProductList />
                </Right.Container>
        </Contents.Container>
      </Container>
    </>
  );
}

export default MyPage;
