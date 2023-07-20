import styled from "styled-components";

const Contents = {
  Container: styled.div`
    margin-top: 20px;
    display: flex;
    width: 100%;
    overflow-x: auto;
    padding-bottom: 20px;
    gap: 30px;
    display: flex;
    &::-webkit-scrollbar {
      height: 5px;
      width: 5px;
      background: #fff3;
      -webkit-border-radius: 1ex;
    }
    &::-webkit-scrollbar-thumb {
      background: white;
      -webkit-border-radius: 1ex;
    }
    &::-webkit-scrollbar-corner {
      background: #fff3;
    }
  `,
};

export { Contents };
