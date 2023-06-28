import { colors } from "@mui/material";
import styled from "styled-components";
import { background } from "../globalStyles";

const Container = styled.div`
  width: 100%;
  min-height: calc(100vh);
  background-color: #000305;
  display: flex;
  justify-content: center;
  color: #fff;
  font-weight: 700;
`;
const Contents = {
  Container: styled.div`
    width: 1400px;
    margin-top: 110px;
    margin-bottom: 100px;
    display: flex;
  `,
};
const Left = {
  Contents: styled.div`
    flex: 1.5;
    display: flex;
    flex-direction: column;
  `,
};
const LeftUp = {
  Content: styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    gap: 10px;
    padding: 10px;
    flex: 2;
  `,
  Title: styled.div`
    font-size: 30px;
  `,
};
const LeftDown = {
  Content: styled.div`
    padding: 10px;
    flex: 1;
  `,
  Title: styled.div`
    flex: 1;
    font-size: 30px;
  `,
  PriceMenu: styled.div`
    display: flex;
    padding-bottom: 15px;
    border-bottom: 1px solid #1a1d1e;
    justify-content: space-between;
  `,
  PriceTitle: styled.div`
    font-size: 20px;
    flex: 1;
    text-align: center;
  `,
};
const Right = {
  Contents: styled.div`
    flex: 1;
    display: flex;
    flex-direction: column;
  `,
};
const RightUp = {
  Content: styled.div`
    display: flex;
    justify-content: center;
    align-items: center;
    flex: 2;
    padding: 10px;
  `,
  Img: styled.div`
    width: 500px;
    height: 500px;
    padding: 10px;
    border-radius: 10px;
    background-color: ${background.grey};
  `,
  Title: styled.div`
    flex: 1;
    font-size: 30px;
  `,
};
const RightDown = {
  Content: styled.div`
    flex: 1;
    padding: 10px;
  `,
  NFTName: styled.div`
    padding: 0 30px;
    font-size: 35px;
    display: flex;
    justify-content: start;
  `,
  NFTTitle: styled.div`
    padding: 0 25px;
    min-width: 70px;
    text-align: center;
  `,
  NFTContentsContainer: styled.div`
    width: auto;
    display: flex;
    justify-content: start;
    flex-direction: column;
  `,
  NFTContent: styled.div`
    color: #7900e9;
    font-size: 20px;
    text-align: center;
  `,
  NFTPrice: styled.span`
    margin-right: 5px;
  `,
  KLAY: styled.span`
    font-size: 16px;
  `,
  NFTContainer: styled.div`
    padding: 15px 0;
    display: flex;
    justify-content: start;
    padding-bottom: 35px;
  `,
};

export {
  Container,
  Contents,
  Left,
  LeftUp,
  LeftDown,
  Right,
  RightUp,
  RightDown,
};
