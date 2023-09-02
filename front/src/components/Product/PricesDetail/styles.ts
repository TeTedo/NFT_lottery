import styled from "styled-components";

const Container = styled.div`
  background-color: #1a1d1e;
  margin-top: 10px;
  margin-bottom: 10px;
  border-radius: 20px;
`;
const Contents = styled.div`
  display: flex;
  justify-content: space-between;
  padding: 3px;
  font-weight: 600;
  text-align: center;
  font-size: 18px;
`;

const Price = styled.div`
  flex: 1;
`;
const Quantity = styled.div`
  flex: 1;
`;
const From = styled.div`
  flex: 1;
`;

export { Container, Contents, Price, Quantity, From };
