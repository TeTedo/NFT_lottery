import styled from "styled-components";
import { background } from "../../../pages/globalStyles";

const Contents = {
  Container: styled.div`
    margin: 30px 0;
    padding: 20px;
    height: 400px;
    background-color: ${background.grey};
    border-radius: 20px;
  `,
  Title: styled.div`
    font-size: 27px;
  `,
};
const NFT = {
  Container: styled.div`
    margin-top: 30px;
    display: flex;
    justify-content: center;
  `,
  Contents: styled.div`
    width: 65%;
  `,
  Title: styled.div`
    font-size: 35px;
  `,
  Img: styled.div`
    width: 300px;
    height: 300px;
    border-radius: 20px;
    border: 1px solid #fff;
  `,
};

export { Contents, NFT };
