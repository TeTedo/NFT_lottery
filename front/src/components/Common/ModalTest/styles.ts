// import { background, colors } from "pages/globalStyles";
import { Input, TextField } from "@mui/material";
import { DatePicker } from "@mui/x-date-pickers";
import { background, colors } from "pages/globalStyles";
import styled from "styled-components";

const ModalItem = {
  Container: styled.div`
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    position: fixed;
    z-index: 100;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(0, 0, 0, 0.5);
  `,
  Box: styled.div`
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 700px;
    height: 500px;
    border: 1px solid ${background.grey};
    background-color: ${background.background1};
    border-radius: 25px;
    color: ${colors.white};
  `,
  Title: styled.div`
    margin-top: 30px;
    font-size: 25px;
  `,
  TextInput: styled(TextField)`
    flex: 1;
    color: ${colors.white};
    background-color: ${colors.white};
    border-radius: 8px;
    label.Mui-focused {
      color: ${colors.purple}; // 라벨 색상을 변경합니다.
    }
    .MuiOutlinedInput-root {
      &.Mui-focused fieldset {
        border-color: ${colors.purple}; // 선 색상을 변경합니다.
      }
    }
  `,
  CustomDatePicker: styled(DatePicker)`
    flex: 1;
    border-radius: 8px;
    .MuiInputBase-root {
      background-color: ${colors.white}; // 배경색 변경
      /* border: 1px solid ${colors.purple}; // 선 색상 변경 */
      &.Mui-focused fieldset {
        border-color: ${colors.purple}; // 선 색상을 변경합니다.
      }
    }
    &.Mui-selected {
      background-color: ${colors.purple} !important;
    }
  `,
  Button: styled.button`
    width: 100%;
    background-color: ${colors.purple};
    border: 0;
    font-size: 16px;
    color: ${colors.white};
    box-shadow: 0;
    /* margin-left: 15px; */
    border-radius: 10px;
    padding: 20px 25px;
    transition: all 0.3s ease-in-out;
    font-weight: 700;
    cursor: pointer;
  `,
};

export { ModalItem };
