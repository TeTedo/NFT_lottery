import React, { useState } from "react";
import { Card } from "./styles";
import { useNavigation } from "hooks/useNavigation";
import Modal from "components/Common/ModalTest";

export default function HoldingProductCard() {
  const { goToDetail } = useNavigation();
  const [isModal, setIsModal] = useState<boolean>(false);
  const openModal = () => {
    setIsModal(true);
  };
  return (
    <>
      <Modal isModal={isModal} setIsModal={setIsModal} />
      <Card.Container>
        <Card.Img onClick={() => goToDetail("1")} />
        <Card.Contents>
          <Card.Title>product description</Card.Title>
          <Card.Flex>
            <Card.Button onClick={openModal}>Sell</Card.Button>
            <Card.Div>
              <Card.Description>price</Card.Description>
              <Card.Time>left time</Card.Time>
            </Card.Div>
          </Card.Flex>
        </Card.Contents>
      </Card.Container>
    </>
  );
}
