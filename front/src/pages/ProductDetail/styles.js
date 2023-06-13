import styled from "styled-components";

const Container = styled.div`
  width: 100vw;
  min-height: calc(100vh);
  background-color: #000305;
  display: flex;
  justify-content: center;
  color: #fff;
`;
const Contents = styled.div`
  width: 1400px;
  border: 1px solid #fff;
  margin-top: 110px;
  margin-bottom: 100px;
  display: flex;
`;
const LeftUpContent = styled.div`
  border: 1px solid #fff;
  flex: 2;
`;
const LeftDownContent = styled.div`
  border: 1px solid #fff;
  flex: 1;
`;
const RightUpContent = styled.div`
  border: 1px solid #fff;
  flex: 2;
`;
const RightDownContent = styled.div`
  border: 1px solid #fff;
  flex: 1;
`;
const LeftContents = styled.div`
  flex: 1;
  display: flex;
  flex-direction: column;
`;
const RigthContents = styled.div`
  flex: 2.5;
  display: flex;
  flex-direction: column;
`;

export {
  Container,
  Contents,
  LeftUpContent,
  LeftDownContent,
  RightUpContent,
  RightDownContent,
  LeftContents,
  RigthContents,
};
