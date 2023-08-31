import styled from "styled-components";
import { background, colors } from "../../../pages/globalStyles";

const Contents = {
  Container: styled.div`
    margin: 70px 0;
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
    display: block;
    width: 65%;
  `,
  Title: styled.div`
    font-size: 35px;
  `,
  Img: styled.div`
    width: 270px;
    height: 270px;
    border-radius: 20px;
    border: 1px solid #fff;
  `,
  Data: {
    Container: styled.div`
      margin-top: 20px;
      display: flex;
      align-items: center;
      font-size: 25px;
      gap: 90px;
      text-align: center;
    `,
    Content: styled.div``,
    Title: styled.div``,
    Description: styled.div`
      color: ${colors.purple};
      font-size: 30px;
    `,
  },
  Box: {
    Container: styled.div`
      display: flex;
      padding: 0 30px;
      justify-content: space-between;
      align-items: center;
      font-size: 20px;
      border-radius: 20px;
      margin-top: 30px;
      width: 80%;
      height: 150px;
      background-color: ${background.background2};
    `,
    Text: styled.div`
      color: ${colors.grey};
    `,
    Time: styled.div``,
    Button: styled.button`
      border: none;
      background-color: ${background.purple};
      color: ${colors.white};
      border-radius: 10px;
      width: 250px;
      height: 70%;
      font-size: 30px;
      font-family: 700;
    `,
  },
};

export { Contents, NFT };
