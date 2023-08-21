import { useNavigation } from "hooks/useNavigation";
import { Card } from "./styles";

export default function ProductCard() {
  const { goToDetail } = useNavigation();

  return (
    <>
      <Card.Container onClick={() => goToDetail(1)}>
        <Card.Img />
        <Card.Contents>
          <Card.Title>product description</Card.Title>
          <Card.Description>price</Card.Description>
          <Card.Time>left time</Card.Time>
        </Card.Contents>
      </Card.Container>
    </>
  );
}
