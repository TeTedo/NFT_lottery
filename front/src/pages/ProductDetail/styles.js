import styled from "styled-components";

const Container = styled.div`
  /* overflow: hidden; */
  width: 100%;
  min-height: calc(100vh);
  background-color: #000305;
  display: flex;
  justify-content: center;
  color: #fff;
  font-weight: 700;
`;
const Contents = styled.div`
  width: 1400px;
  margin-top: 110px;
  margin-bottom: 100px;
  display: flex;
`;
const LeftUpContent = styled.div`
  padding: 10px;
  flex: 2;
`;
const LeftDownContent = styled.div`
  padding: 10px;
  flex: 1;
`;
const LeftUpTitle = styled.div`
  flex: 2;
  font-size: 30px;
`;
const RightUpContent = styled.div`
  display: flex;
  flex: 2;
  padding: 10px;
`;

const RightDownContent = styled.div`
  flex: 1;
  padding: 10px;
`;
const LeftContents = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
`;
const RightContents = styled.div`
  flex: 2.5;
  display: flex;
  flex-direction: column;
`;
const NFTImg = styled.div`
  flex: 1;
  padding: 10px;
  border-radius: 20px;
  background-color: gray;
`;
const PriceContainer = styled.div`
  display: flex;
  padding-bottom: 15px;
  border-bottom: 1px solid #1a1d1e;
  justify-content: space-between;
`;
const PriceTitles = styled.div`
  font-size: 20px;
  flex: 1;
  text-align: center;
`;

export {
  Container,
  Contents,
  LeftUpContent,
  LeftDownContent,
  RightUpContent,
  RightDownContent,
  LeftContents,
  RightContents,
  NFTImg,
  LeftUpTitle,
  PriceContainer,
  PriceTitles,
};
