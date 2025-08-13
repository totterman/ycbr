import { StyleSheet, Text, View } from 'react-native';

export default function AboutScreen() {
      
  return (
    <View style={styles.container}>
      <Text style={styles.text}>This is the Yacht Club booat register and inspection app.</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#25292e',
    justifyContent: 'center',
    alignItems: 'center',
  },
  text: {
    color: '#fff',
    fontSize: 20,
  },
});
