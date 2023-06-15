import styled from "styled-components";

const TopbBarContainer = styled.div`
  position: fixed;
  box-sizing: border-box;
  width: 100%;
  height: 60px;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: 8px 40px 8px 40px;
  background-color: rgba(0, 0, 0, 0.1);
  align-content: center;
  flex-wrap: nowrap;
  color: #fff;
  border-bottom: 1px solid #fff;  
`;

export { TopbBarContainer };
